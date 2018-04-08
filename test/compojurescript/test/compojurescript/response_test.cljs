(ns compojurescript.test.compojurescript.response-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [compojurescript.response :as response]
            [compojurescript.test.mock :as mock :refer [future promise]]))

(def expected-response
  {:status  200
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :body    "<h1>Foo</h1>"})


(defmulti handler-multi :body)

(defmethod handler-multi :default [request]
  expected-response)

(deftest render-test
  (testing "with nil"
    (is (nil? (response/render nil {}))))

  (testing "with string"
    (is (= (response/render "<h1>Foo</h1>" {})
           expected-response)))

  (testing "with string seq"
    (let [response (response/render '("<h1>" "Foo" "</h1>") {})]
      (is (seq? (:body response)))
      (is (= (:headers response)
             {"Content-Type" "text/html; charset=utf-8"}))))

  (testing "with handler function"
    (is (= (response/render (constantly expected-response) {})
           expected-response)))

  (testing "with handler multimethod"
    (is (= (response/render handler-multi {})
           expected-response)))

  (testing "with deref-able"
    (is (= (response/render (future expected-response) {})
           expected-response)))

  (testing "with map + metadata"
    (let [response (response/render ^{:has-metadata? true} {:body "foo"} {})]
      (is (= (:body response) "foo"))
      (is (= (meta response) {:has-metadata? true}))))

  (testing "with vector"
    (is (thrown-with-msg? js/Error
                          #"No protocol method Renderable.render defined for type"
                          (response/render [] {})))))

(deftest send-test
  (testing "render fallback"
    (let [response  (promise)
          exception (promise)]
      (response/send "foo" {} response exception)
      (is (not (realized? exception)))
      (is (= @response
             {:status  200
              :headers {"Content-Type" "text/html; charset=utf-8"}
              :body    "foo"}))))

  (testing "function return value"
    (let [response  (promise)
          exception (promise)]
      (response/send (fn [_ respond _] (respond "bar")) {} response exception)
      (is (not (realized? exception)))
      (is (= @response
             {:status  200
              :headers {"Content-Type" "text/html; charset=utf-8"}
              :body    "bar"})))))

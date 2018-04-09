(ns compojurescript.mock
  (:require [compojurescript.response :as resp]))

(defn request
  ([method uri]
   (request method uri nil))
  ([method uri params]
   {:uri uri :request-method method :params params}))

(defn promise []
  (let [a (atom nil)]
    (reify
      cljs.core/IFn
      (-invoke [_ x] (reset! a x))
      cljs.core/IDeref
      (-deref [_] (deref a))
      cljs.core/IPending
      (-realized? [_] (not (nil? (deref a))))
      resp/Renderable
      (resp/render [ref request] (resp/render (deref ref) request)))))

(defn future [y]
  (let [a (atom y)]
    (reify
      ;cljs.core/IFn
      ;(-invoke [_ x] (reset! a x))
      cljs.core/IDeref
      (-deref [_] (deref a))
      cljs.core/IPending
      (-realized? [_] (not (nil? (deref a))))
      resp/Renderable
      (resp/render [ref request] (resp/render (deref ref) request)))))

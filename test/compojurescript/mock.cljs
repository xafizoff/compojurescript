(ns compojurescript.mock
  (:require [compojurescript.response :as resp]))

(defn request
  ([method uri]
   (request method uri nil))
  ([method uri params]
   {:uri uri :request-method method :params params}))

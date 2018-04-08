(ns compojurescript.response
  "A protocol for generating Ring response maps"
  (:refer-clojure :exclude [send])
  (:require [macchiato.util.mime-type :as mime]
            [macchiato.util.response :as response]))

(defn response
  "Returns a skeletal Macchiato response with the given body, status of 200, and no
  headers."
  [body]
  {:status  200
   :headers {}
   :body    body})

(defprotocol Renderable
  "A protocol that tells Compojure how to handle the return value of routes
  defined by [[GET]], [[POST]], etc.
  This protocol supports rendering strings, maps, functions, refs, files, seqs,
  input streams and URLs by default, and may be extended to cover many custom
  types."
  (render [x request]
          "Render `x` into a form suitable for the given request map."))

(defprotocol Sendable
  "A protocol that tells Compojure how to handle the return value of
  asynchronous routes, should they require special attention."
  (send* [x request respond raise]))

(defn send
  "Send `x` as a Ring response. Checks to see if `x` satisfies [[Sendable]],
  and if not, falls back to [[Renderable]]."
  [x request respond raise]
  (if (satisfies? Sendable x)
    (send* x request respond raise)
    (respond (render x request))))

(defn- guess-content-type [response name]
  (if-let [mime-type (mime/ext-mime-type (str name))]
    (response/content-type response mime-type)
    response))

(extend-protocol Renderable
  nil
  (render [_ _] nil)
  string
  (render [body _]
    (-> (response body)
        (response/content-type "text/html; charset=utf-8")))
  List
  (render [coll _]
    (-> (response coll)
        (response/content-type "text/html; charset=utf-8")))
  cljs.core/PersistentArrayMap
  (render [resp-map _]
    (merge (with-meta (response "") (meta resp-map))
           resp-map))
  cljs.core/PersistentHashMap
  (render [resp-map _]
    (merge (with-meta (response "") (meta resp-map))
           resp-map))
  function
  (render [func request] (render (func request) request))
  cljs.core/MultiFn
  (render [func request] (render (func request) request))
  #_IDeref
  #_(render [ref request] (render (deref ref) request)))

(extend-protocol Sendable
  function
  (send* [func request respond raise]
    (func request #(send % request respond raise) raise))
  cljs.core/MultiFn
  (send* [func request respond raise]
    (func request #(send % request respond raise) raise)))

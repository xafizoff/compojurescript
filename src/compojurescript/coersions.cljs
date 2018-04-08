(ns compojurescript.coersions
  (:require [goog.math.Long :as Long]))

(defn as-int
  "Parse a string into an integer, or `nil` if the string cannot be parsed."
  [s]
  (when-not (empty? s)
    (let [i (js/Number. s)]
      (when (and
             (not ^boolean (js/isNaN i))
             (= (js/parseFloat i) (js/parseInt i)))
        (int i)))))

(def ^:private uuid-pattern
  #"[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}")

(defn as-uuid
  "Parse a string into a UUID, or `nil` if the string cannot be parsed."
  [s]
  (if (re-matches uuid-pattern s)
    (uuid s)))

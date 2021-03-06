(defproject compojurescript "0.1.2-SNAPSHOT"
  :description "Compojure for ClojureScript"
  :url "http://github.com/xafizoff/compojurescript"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.10.238" :scope "provided"]
                 [org.clojure/tools.macro "0.1.5"]
                 [medley "1.0.0"]
                 [clout "2.2.1"]]
  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-doo "0.1.7"]]
  :cljsbuild {:builds
              {:test
               {:source-paths ["src" "test"]
                :compiler     {:main          compojurescript.test-runner
                               :output-dir    "target/test/out"
                               :output-to     "target/test/core.js"
                               :target        :nodejs
                               :optimizations :none
                               :source-map    true
                               :pretty-print  true}}}}
  :doo {:build "test"}
  :aliases
  {"test"
   ["do"
    ["clean"]
    ["doo" "node" "once"]]})

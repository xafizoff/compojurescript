(ns compojurescript.test-runner
  (:require
    [doo.runner :refer-macros [doo-tests]]
    [compojurescript.core-test]
    [compojurescript.coersions-test]
    [compojurescript.response-test]))

(doo-tests 'compojurescript.coersions-test
           'compojurescript.response-test
           'compojurescript.core-test)

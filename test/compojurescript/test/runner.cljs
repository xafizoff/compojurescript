(ns compojurescript.test.runner
  (:require
    [doo.runner :refer-macros [doo-tests]]
    [compojurescript.test.compojurescript.core-test]
    [compojurescript.test.compojurescript.coersions-test]
    [compojurescript.test.compojurescript.response-test]))

(doo-tests 'compojurescript.test.compojurescript.coersions-test
           'compojurescript.test.compojurescript.response-test
           'compojurescript.test.compojurescript.core-test)

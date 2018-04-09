# CompojureScript

[Compojure] for ClojureScript. Works both in the node.js environment and in browsers

[![Build Status](https://travis-ci.org/xafizoff/compojurescript.svg?branch=master)](https://travis-ci.org/xafizoff/compojurescript)
[![Clojars Project](https://img.shields.io/clojars/v/compojurescript.svg)](https://clojars.org/compojurescript)

## Usage

```clojure
[compojurescript "0.1.1"]

```
Here's simple example of use:
```clojure
(ns hello-world.core
  (:require [compojurescript.core :refer-macros [defroutes GET]]
            [macchiato.util.response :refer [not-found]]))

(defroutes app
  (GET "/" [] "<h1>Hello World</h1>")
  (not-found "<h1>Page not found</h1>"))
```

See [Compojure] docs for advanced usage

## License

Copyright © 2018 Marat Khafizov, James Reeves

Distributed under the Eclipse Public License version 1.0

[Compojure]: https://github.com/weavejester/compojure
[macchiato-framework]: https://github.com/macchiato-framework

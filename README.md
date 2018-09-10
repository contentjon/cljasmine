# cljasmine

A ClojureScript BDD testing framework based on
[Jasmine](https://jasmine.github.io/) that displays your test
results in a browser page and comes with auto-refresh.

## Syntax

```clj
(ns my.test.ns
  (:require-macros [cljasmine.macros :as j])
  (:require [cljasmine.checkers]))

(j/describe "The value true"
  (j/it "should be true"
    (j/expect true := true))
  (j/it "should not be false"
    (j/expect true :not= false)))
```

## Current Limitations

- When changing a file in your source folder, you won't see any
  changes in your browser until you touch a file in your test
  folder. That is because we haven't yet found a way to make `lein
  cljsbuild auto` do what we want (see [lein-cljsbuild issue
  159](https://github.com/emezeske/lein-cljsbuild/issues/159))
- Hasn't yet been tested with advanced compilation - but this can't be
  too hard either..

## Requirements

- [Leiningen](https://github.com/technomancy/leiningen) `1.7.0` or
  higher to work with [lein-cljsbuild](https://github.com/emezeske/lein-cljsbuild)
- A working Ruby toolchain including [Bundler](http://gembundler.com/).
- If you want the auto-refresh (you do), you'll need the
  [LiveReload Browser Extension](http://go.livereload.com/extensions).

## Trying it out

- Clone this repository and `cd` into it.
- Do `bundle install`.
- Do `foreman start`.
- You might get a `TSP server error: Address already in use`. Not sure
  how this happens, but it will work anyways oO
- Point your browser to
  [http://localhost:8888](http://localhost:8888/). You should see a few
  passing specs now.
- Enable the LiveReload plugin now, if you installed it.
- Open `test/cljasmine/core_test.cljs` in an editor of your choice.
- Try to break one of the test cases (for example by replacing one `:<` by `:>`).
- Save the file
- After a few seconds, you'll see the failure in your
  browser. Without LiveReload, you'll need to hit refresh.

## Setup for your own project

- Do `lein install` in the cljasmine directory.
- Copy over the following stuff to your own project directory:
  - The `spec` directory.
  - `Gemfile`, `Rakefile`, `Procfile`, and `Guardfile`.
- Configure lein-cljsbuild in your `project.clj` similar to cljasmine's
  own project file. If you want to change the path of `unit-test.js`,
  you'll need to adjust `spec/javascripts/support/jasmine.yml` accordingly.
- Start writing tests and do the same as above.

## Your own checkers

```clj
(j/defchecker :has-key [result key]
  (when-not (get result key)
    ["Actual result:" result
     "doesn't contain the key:" key]))
```

You may also return a string or a keyword for the error message. Now,
you can write:

```clj
(j/describe "The map {:a 1}"
  (j/it "contains the key :a"
    (j/expect {:a 1} :has-key :a)))
```

If you define the checker in another namespace, remember to require it
from the tests.

## Contributing

Awesome idea!

## License

Copyright © 2012 Jan Krüger & Benjamin Teuber

Distributed under the Eclipse Public License, the same as Clojure.

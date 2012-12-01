(defproject cljasmine "0.1.0-SNAPSHOT"
  :description "A ClojureScript BDD testing framework"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-cljsbuild "0.2.9"]]
  :cljsbuild
  {:builds {:test {:source-path "test"
                   :compiler {:output-to "resources/private/js/unit-test.js"
                              :optimizations :whitespace
                              :pretty-print true}}}})

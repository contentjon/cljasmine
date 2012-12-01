(ns cljasmine.core-test
  (:require-macros [cljasmine.macros :as j])
  (:require [cljasmine.checkers]))

(j/describe "Wrapped Jasmine matchers"
  (j/it "should contain :to-be"
    (j/expect (+ 1 2 3) :to-be 6)))

(j/describe "Arithmetic checkers"
  (j/it ":="
    (j/expect (list 1 2 3) := [1 2 3]))
  (j/it ":<"
    (j/expect 5 :< 6))
  (j/it ":<="
    (j/expect 6 :<= 6))
  (j/it ":>"
    (j/expect 7 :> 6))
  (j/it ":>="
    (j/expect 5 :>= 5)))

(j/describe "Other checkers"
  (j/it ":contains"
    (j/expect [1 2 3] :contains [1 2])
    (j/expect {:a 1 :b 2} :contains {:a 1})
    (j/expect {:a {:b 2 :c 3} :d 4} :contains {:a {:c 3}})))

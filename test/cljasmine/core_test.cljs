(ns cljasmine.core-test
  (:require-macros [cljasmine.macros :as j])
  (:require [cljasmine.checkers]))

(j/describe "Equality"
  (j/it ":="
    (j/expect true := true))
  (j/it ":not="
    (j/expect true :not= false)))

(j/describe "Arithmetics"
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

(j/describe "Wrapped Jasmine matchers"
  (j/it "should contain :to-be"
    (j/expect (+ 1 2 3) :to-be 6)))

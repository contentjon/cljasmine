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

(j/describe "Unary checkers"
  (j/it ":truthy"
    (j/is 42 :truthy)
    (j/is [] :truthy)
    (j/is true :truthy))
  (j/it ":falsey"
    (j/is false :falsey)
    (j/is nil :falsey))
  (j/it ":nil"
    (j/is nil :nil))
  (j/it ":not-nil"
    (j/is [] :not-nil)
    (j/is false :not-nil))
  (j/it ":empty"
    (j/is "" :empty)
    (j/is [] :empty)
    (j/is nil :empty))
  (j/it ":not-empty"
    (j/is "a" :not-empty)
    (j/is [1] :not-empty)
    (j/is '(1) :not-empty)))

(j/describe "Floating point checkers"
  (j/it ":to-be-roughly"
    (j/expect 3.0 :to-be-roughly 2.99999)
    (j/expect 3.0 :to-be-roughly 4 :e 1.1)))

(j/describe "Other checkers"
  (j/it ":contains"
    (j/expect [1 2 3] :contains [1 2])
    (j/expect {:a 1 :b 2} :contains {:a 1})
    (j/expect {:a {:b 2 :c 3} :d 4} :contains {:a {:c 3}})))

(j/describe "Wrapped Jasmine matchers"
  (j/it "should contain :to-be"
    (j/expect (+ 1 2 3) :to-be 6)))

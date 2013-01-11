 (ns cljasmine.macros
  (:require [clojure.string :as str]))

(defmacro describe [description & body]
  `(~'js/describe (name ~description)
                  (fn []
                    ~@body
                    nil)))

(defmacro it [description & body]
  `(~'js/it (name ~description)
            (fn []
              ~@body
              nil)))

;; TODO: reuse clojure's own replacements
(def replacements
  {"=" "_EQ_"
   "<" "_LT_"
   ">" "_GT_"})

;; TODO: better use a crossover namespace instead
;;       of a macro for reuse in checkers.cljs?
(defmacro format-js-sym [sym]
  `(let [[fst# & rst#] (-> ~sym
                           (name)
                           ~@(for [[replace with] replacements]
                               `(clojure.string/replace ~replace ~with))
                           (clojure.string/split #"-"))]
     (->> rst#
          (map clojure.string/capitalize)
          (cons fst#)
          (clojure.string/join))))

(defmacro expect [result checker-key & expected]
  (let [checker (-> checker-key
                    (format-js-sym)
                    (symbol))]
    `(. (~'js/expect ~result)
        (~checker ~@expected))))

(defmacro is [& args]
  `(expect ~@args))

(defmacro defchecker [key args & body]
  `(cljasmine.checkers/add-checker
    ~key
    (fn ~args
      ~@body)))

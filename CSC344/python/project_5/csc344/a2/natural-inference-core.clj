; Duncan Zaug
; Project 2
; Natural Inference
(ns natural-inference.core
  (:require clojure.set)
  (:require [clojure.set :as set :refer [union]]))

(defn not-elimination
  [not-prop]
  (if (seq? not-prop)
    (if (empty? not-prop)
      #{}
      (if (= (some #{(symbol "not")} not-prop) (symbol "not"))
        (if (<= 2 (count not-prop))
          (if (seq? (nth not-prop 1))
            (if (= (some #{(symbol "not")} (nth not-prop 1)) (symbol "not"))
              #{(nth (nth not-prop 1) 1)}
              #{})
            #{})
          #{})
        #{})
      )
    #{})
  )

(defn and-elimination
  [and-prop]
  (if (seq? and-prop)
    (if (empty? and-prop)
      #{}
      (if (= (some #{(symbol "and")} and-prop) (symbol "and"))
        (if (<= 3 (count and-prop))
          #{(nth and-prop 1) (nth and-prop 2)}
          #{})
        #{})
      )
    #{})
  )

(defn modus-ponens
  [if-prop kb]
  (let [has-if (first (filter (fn [i]
                                (if (seq? i)
                                  (= (some #{(symbol "if")} i) (symbol "if"))
                                  (= (symbol "if") i)))
                              (conj kb if-prop)))
        no-if (first (remove (fn [i]
                               (if (seq? i)
                                 (= (some #{(symbol "if")} i) (symbol "if"))
                                 (= (symbol "if") i)))
                             (conj kb if-prop)))]
    ;actual code to be executed
    (if (or (nil? has-if) (nil? no-if))
      #{}
      (if (and (seq? has-if) (= 3 (count has-if)))
        (if (= (second has-if) no-if)
          #{(last has-if)}
          #{}
          )
        #{})
      )
    )
  )

(defn modus-tollens
  [if-prop kb]
  (let [has-if (first (filter (fn [i]
                                (if (seq? i)
                                  (= (some #{(symbol "if")} i) (symbol "if"))
                                  (= (symbol "if") i)))
                              (conj kb if-prop)))
        no-if (first (remove (fn [i]
                               (if (seq? i)
                                 (= (some #{(symbol "if")} i) (symbol "if"))
                                 (= (symbol "if") i)))
                             (conj kb if-prop)))]
    ;actual code to be executed
    (if (or (nil? has-if) (nil? no-if))
      #{}
      (if (and (seq? has-if) (= 3 (count has-if)))
        (if (and (seq? no-if) (= (first no-if) (symbol "not")))
          (if (= (second no-if) (last has-if))
            #{(list (symbol "not") (second has-if))}
            #{}
            )
          #{})
        #{})
      )
    )
  )

(defn elim-step
  "One step of the elimination inference procedure."
  ([prop kb]
   (let [prop-kb (conj kb prop)]
     (union
       (not-elimination (first prop-kb))
       (and-elimination (first prop-kb))
       (modus-ponens prop kb)
       (modus-tollens prop kb)
       )
     )
   )
  )

(defn fwd-infer
  [prop kb]
  (loop [eval1 (conj kb prop)
         result (conj kb prop)]
    (if (not (empty? eval1))
      (recur (union (rest eval1) (elim-step (first eval1) (rest eval1)))
             (conj result (first eval1)))
      result)
    )
  )


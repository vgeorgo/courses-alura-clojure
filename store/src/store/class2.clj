(ns store.class2)

; My count with more than one function definition. For optional parameters.
(defn my-count
  ; First variation (default second parameter)
  ([elements]
   (my-count elements 0))

  ; Second variation of the function
  ([elements, initial-count]
   (if (seq elements)
     (recur (next elements) (inc initial-count))   ; if
     initial-count)))                              ; else

(println "Count: " (my-count [1 2 3 4 5]))
(println "Count empty: " (my-count []))
(println "Count initial value 10: "  (my-count [] 10))

; My count with loop. Not always a good practice because it can raise the complexity of the function,
; since it is possible to add code before the loop. We should avoid increasing the complexity.
; In the example below, it would be better to use the previous recursive implementation
(defn my-count2
  ; First variation (default second parameter)
  ([elements]
   (loop [initial-count 0
          next-elements elements]
     (if (seq next-elements)
       (recur (inc initial-count) (next next-elements))   ; if
       initial-count))))                                  ; else

(println "2 - Count: " (my-count2 [1 2 3 4 5]))
(println "2 - Count empty: " (my-count2 []))
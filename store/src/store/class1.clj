(ns store.class1)

; Creating our own map function
; This recursion can generate a Stack Overflow error, since its stacking one execution into another
(defn my-map [callable collection]
  (let [item (first collection)]
    (if (not (nil? item))
      (do
        (callable item)
        (my-map callable (rest collection))))))

(def list-normal [1 2 3])
(def list-false [1 false 3])

(println "List normal: ")
(my-map println list-normal)

(println "List false: ")
(my-map println list-false)

; This will generate stack error
; (my-map println (range 100000))

; To avoid the Stack problem mentioned earlier we need to tell Clojure that it is a recursion
(defn my-map [callable collection]
  (let [item (first collection)]
    (if (not (nil? item))
      (do
        (callable item)
        (recur callable (rest collection))))))              ; Tail recursion

; This will not generate stack error anymore
; (my-map println (range 100000))
(ns store.class5
  (:require [store.db])
  (:require [store.logic]))

(defn spent-a-lot-bool? [user-summary]
  (> (:total-spent user-summary) 500))

(defn spent-a-lot-bool? [user-summary]
  (> (:total-spent user-summary) 500))

(let [orders (store.db/all-orders)
      summary (store.logic/summary-orders-spent-by-user orders)]

  ; Keep is equivalent to map + filter, because it keeps what is returned by the function, excepts nil
  (println "Keep spent-a-lot?: " (keep spent-a-lot-bool? summary))
  (println "Filter spent-a-lot?: " (filter spent-a-lot-bool? summary)))

; Lazy
(println (take 2 (range 10)))
; Takes the same time as the previous because the range is not generated entirely as soon as it is called,
; but only when take is trying to get the elements.
(println (take 2 (range 100000000000000)))


(defn handler1 [item]
  (println "handler1")
  item)
(defn handler2 [item]
  (println "handler2")
  item)

; Map seems eager. Solves first handler 1 and after handler 2 before printing the result
(println (map handler2 (map handler1 (range 10))))

; But in fact it operates in both eager and lazy, it operates in chunks
(->> (range 50)
     (map handler1)
     (map handler2)
     println)

; mapv creates a vector in memory, which forces it to be eager
(->> (range 50)
     (mapv handler1)
     (mapv handler2)
     println)

; Vectors are also mixed with eager + lazy
(->> [1 2 3 4 5 6 1 1 2 3 4 5 6 7 3 3 1 4 6 7 2 4 2 0 9 7 3 2 1 3 5 6 2 3 5 6 1 4 6 8 2 3 5 1 3 5 6 1 4 2 6 8 4 2 3 1 2 5 6 1]
     (map handler1)
     (map handler2)
     println)

; Lists are completely lazy. No chunks
(->> '(1 2 3 4 5 6 1 1 2 3 4 5 6 7 3 3 1 4 6 7 2 4 2 0 9 7 3 2 1 3 5 6 2 3 5 6 1 4 6 8 2 3 5 1 3 5 6 1 4 2 6 8 4 2 3 1 2 5 6 1)
     (map handler1)
     (map handler2)
     println)
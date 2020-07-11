(ns hospital.logic)

(defn space-in-queue? [hospital department]
  ; Thread first (previous variable will be added as the first symbol on the next operation)
  (-> hospital
      (get department)
      count
      (< 5)))

(defn person-arrives [hospital department person]
  (if (space-in-queue? hospital department)
    (update hospital department conj person)
    (throw (ex-info "Queue is full" {:person person}))))

(defn person-arrives-with-sleep
  [hospital department person]
  (println "Trying to add person: " person)
  (Thread/sleep (* (rand) 2000))
  (if (space-in-queue? hospital department)
    (do
      ; (Thread/sleep (* (rand) 2000))
      (println "Updating person: " person)
      (update hospital department conj person))
    (throw (ex-info "Queue is full" {:person person}))))

(defn next-in-line
  "Gets the next person from the department queue"
  [hospital department]
  (-> hospital
      department
      peek))

(defn call-person
  "Removes the first person from the department queue"
  [hospital department]
  (update hospital department pop))

; Variation of the call-person returning not only the updated hospital,
; but also the person popped.
(defn call-person-complete
  "Returns the next person and the updated hospital without him"
  [hospital department]
  {:person   (next-in-line hospital department)
   :hospital (call-person hospital department)})

; Variation of the call-person returning not only the updated queue,
; but also the person popped.
(defn call-person-juxt
  "Returns the next person and the updated queue without him"
  [hospital department]
  (let [peek-pop (juxt next-in-line call-person)
        [person updated-queue] (peek-pop (get hospital department))]
    {:person person
     :queue  updated-queue}))

(defn transfer
  "Transfer the first person from one department to another"
  [hospital from to]
  (let [person (next-in-line hospital from)]
    (-> hospital
        (call-person from)
        (person-arrives to person))))
package service

import akka.actor.{Terminated, ActorRef, ActorLogging, Actor}

class Master extends Actor with ActorLogging {

  import models.MasterWorkerProtocol._
  import scala.collection.mutable.{Map, Queue}
  
  val workers = Map.empty[ActorRef, Option[Tuple2[ActorRef, Any]]]
  val workQ = Queue.empty[Tuple2[ActorRef, Any]]
  
  def notifyWorkers(): Unit = {
    if (!workQ.isEmpty) {
      workers.foreach {
        case (worker, m) if (m.isEmpty) => worker ! WorkIsReady
        case _ =>
      }
    }
  }
  
  def receive = {
    case WorkerCreated(worker) =>
      log.info("Worker created: {}", worker)
      context.watch(worker)
      workers += (worker -> None)
      notifyWorkers()

    case WorkerRequestsWork(worker) =>
      log.info("Worker requests work: {}", worker)
      if (workers.contains(worker)) {
        if (workQ.isEmpty)
          worker ! NoWorkToBeDone
        else if (workers(worker) == None) {
          val (workSender, work) = workQ.dequeue()
          workers += (worker -> Some(workSender -> work))
          worker.tell(WorkToBeDone(work), workSender)
        }
      }

    case WorkIsDone(worker) =>
      if (!workers.contains(worker))
        log.error("Blurgh! {} said it's done work but we didn't know about him", worker)
      else
        workers += (worker -> None)

    case Terminated(worker) =>
      if (workers.contains(worker) && workers(worker) != None) {
        log.error("Blurgh! {} died while processing {}", worker, workers(worker))
        val (workSender, work) = workers(worker).get
        self.tell(work, workSender)
      }
      workers -= worker

    case work =>
      log.info("Queueing {}", work)
      workQ.enqueue(sender -> work)
      notifyWorkers()
  }
  
}

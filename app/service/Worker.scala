package service

import akka.actor.{ActorRef, Actor, ActorLogging, ActorPath}

abstract class Worker(masterLocation: ActorPath) extends Actor with ActorLogging {
  import models.MasterWorkerProtocol._
  
  val master = context.actorSelection(masterLocation)
  
  case class WorkComplete(result: Any)
  
  def doWork(workSender: ActorRef, work: Any): Unit
  
  override def preStart() = master ! WorkerCreated(self)
  
  def working(work: Any): Receive = {
    case WorkIsReady =>
    case NoWorkToBeDone =>
    case WorkToBeDone(_) =>
      log.error("Yikes. Master told me to do work, while i'm working.")
    case WorkComplete(result) =>
      log.info("Work is complete. Result {}", result)
      master ! WorkIsDone(self)
      master ! WorkerRequestsWork(self)
    
      context.become(idle)
  }

  
  def idle: Receive = {
    case WorkIsReady =>
      log.info("Requesting work")
      master ! WorkerRequestsWork(self)
    case WorkToBeDone(work) =>
      log.info("Got work {}", work)
      doWork(sender, work)
      context.become(working(work))
    case NoWorkToBeDone =>
  }
  
  def receive = idle
}

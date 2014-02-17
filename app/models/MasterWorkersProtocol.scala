package models

import akka.actor.ActorRef

object MasterWorkerProtocol {
	// Message from Workers
	case class WorkerCreated(worker: ActorRef)
	case class WorkerRequestsWork(worker: ActorRef)
	case class WorkIsDone(worker: ActorRef)

	// Messages to Workers
	case class WorkToBeDone(work: Any)
	case object WorkIsReady
	case object NoWorkToBeDone
}
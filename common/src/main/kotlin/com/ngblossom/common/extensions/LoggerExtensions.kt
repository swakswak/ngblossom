package com.ngblossom.common.extensions

import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.message.ObjectMessage

fun Logger.info(objectMessage: Any) = this.info(ObjectMessage(objectMessage))

fun Logger.info(vararg pairs: Pair<String, Any>) = this.info(ObjectMessage(mapOf(*pairs)))
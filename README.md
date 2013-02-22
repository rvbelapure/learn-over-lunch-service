learn-over-lunch-service
========================

Webservice backend support for Learn-Over-Lunch Android App

Communication Protocol :

Endpoint		Consume					Produce
=================================================================================================
/userservice
	/auth		(str) <uname>::<passwd>			(str) RESP_YES / RESP_NO
	/signup		(json) <full profile>			(str) RESP_YES / RESP_NO
	/profile	(json) myuname, profileuname		(json) errorcode, isFriend, <full profile>
	/friend
		/get	(str) uname				(jsonarr) arr of jsons - profiles
		/add	(json) myuname, profileuname		(str), RESP_NO / RESP_YES / RESP_MALFORMED
		/accept	(json) myuname, profileuname		(str), RESP_NO / RESP_YES / RESP_MALFORMED

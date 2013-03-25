learn-over-lunch-service
========================

Webservice backend support for Learn-Over-Lunch Android App

Communication Protocol :

Endpoint			Consume					Produce
==================================================================================================================
/userservice
	/auth			(str) <uname>::<passwd>			(str) RESP_YES / RESP_NO
	/signup			(json) <full profile>			(str) RESP_YES / RESP_NO
	/profile		(json) myuname, profileuname		(json) errorcode, isFriend, <full profile>
	/getname		(str) <uname>				(str) <fname>' '<lname>
	/friend
		/get		(str) uname				(jsonarr) arr of jsons - profiles
		/add		(json) myuname, profileuname		(str) RESP_NO / RESP_YES / RESP_MALFORMED
		/accept		(json) myuname, profileuname		(str) RESP_NO / RESP_YES / RESP_MALFORMED
	/rateuser		(str) <uname>::<rating>			(str) RESP_NO / RESP_YES / RESP_MALFORMED

/eventservice
	/create			(json) <event details, uname>		(str) RESP_MALFORMED / RESP_YES / RESP_NO
	/get
	     /event
	    	/byuser		(str) uname				(json) arr of jsons - events
		/bycategory	(str) category				(json) arr of jsons - events
	    	/bylocation	(str) location				(json) arr of jsons - events
	     /members		(str) eventid				(json) arr of jsons - profiles
	/isMember		(json) uname, event_id			(str) RESP_NO / RESP_YES / RESP_MALFORMED
	/eventjoin		(json) uname, event_id			(str) RESP_NO / RESP_YES / RESP_MALFORMED

/categoryservice
	/get			<nothing>				(json) arr of jsons - categories

/locationservice
	/get			<nothing>				(json) arr of jsons - locations

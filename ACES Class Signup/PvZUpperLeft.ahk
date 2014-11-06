full = 1
Run "C:\Program Files (x86)\Plants vs Zombies 2012 Final\PlantsVsZombies.exe"
Process, Wait, popcapgame1.exe
Process, Exist, popcapgame1.exe
PID = %ErrorLevel%
GroupAdd, Group1, ahk_pid %PID%
Process, WaitClose, %PID%
ExitApp
#IfWinActive ahk_group Group1
{
	!Enter::
	full := full<1 ? 1 : 0
	Send !{Enter}
	return
	
	;Slots
	{
		1::
		MouseGetPos, xpos, ypos
		if full = 0
			Click 444, 70
		else
			Click 125, 70
		Click %xpos%, %ypos%, 0
		return
		2::
		MouseGetPos, xpos, ypos
		if full = 0
			Click 536, 70
		else
			Click 167, 70
		Click %xpos%, %ypos%, 0
		return
		3::
		MouseGetPos, xpos, ypos
		if full = 0
			Click 628, 70
		else
			Click 218, 70
		Click %xpos%, %ypos%, 0
		return
		4::
		MouseGetPos, xpos, ypos
		if full = 0
			Click 720, 70
		else
			Click 269, 70
		Click %xpos%, %ypos%, 0
		return
		5::
		MouseGetPos, xpos, ypos
		if full = 0
			Click 812, 70
		else
			Click 320, 70
		Click %xpos%, %ypos%, 0
		return
		6::
		MouseGetPos, xpos, ypos
		if full = 0
			Click 904, 70
		else
			Click 371, 70
		Click %xpos%, %ypos%, 0
		return
		7::
		MouseGetPos, xpos, ypos
		if full = 0
			Click 996, 70
		else
			Click 422, 70
		Click %xpos%, %ypos%, 0
		return
		8::
		MouseGetPos, xpos, ypos
		if full = 0
			Click 1088, 70
		else
			Click 473, 70
		Click %xpos%, %ypos%, 0
		return
		9::
		MouseGetPos, xpos, ypos
		if full = 0
			Click 1180, 70
		else
			Click 524, 70
		Click %xpos%, %ypos%, 0
		return
		0::
		MouseGetPos, xpos, ypos
		if full = 0
			Click 1272, 70
		else
			Click 575, 70
		Click %xpos%, %ypos%, 0
		return
		-::
		MouseGetPos, xpos, ypos
		if full = 0
			Click 1400, 60
		else
			Click 646, 64
		Click %xpos%, %ypos%, 0
		return
	}
	
	;Zen Garden
	{
		q::
		MouseGetPos, xpos, ypos
		if full = 0
			Click 305, 60
		else
			Click 38, 70
		Click %xpos%, %ypos%, 0
		return
		w::
		MouseGetPos, xpos, ypos
		if full = 0
			Click 431, 60
		else
			Click 108, 60
		Click %xpos%, %ypos%, 0
		return
		e::
		MouseGetPos, xpos, ypos
		if full = 0
			Click 557, 60
		else
			Click 178, 60
		Click %xpos%, %ypos%, 0
		return
		r::
		MouseGetPos, xpos, ypos
		if full = 0
			Click 683, 60
		else
			Click 248, 60
		Click %xpos%, %ypos%, 0
		return
		t::
		MouseGetPos, xpos, ypos
		if full = 0
			Click 809, 60
		else
			Click 318, 60
		Click %xpos%, %ypos%, 0
		return
		y::
		MouseGetPos, xpos, ypos
		if full = 0
			Click 935, 60
		else
			Click 388, 60
		Click %xpos%, %ypos%, 0
		return
		u::
		MouseGetPos, xpos, ypos
		if full = 0
			Click 1061, 60
		else
			Click 458, 60
		Click %xpos%, %ypos%, 0
		return
		i::
		MouseGetPos, xpos, ypos
		if full = 0
			Click 1187, 60
		else
			Click 528, 60
		Click %xpos%, %ypos%, 0
		return
	}
	
	;Codes
	{
		a::Send mustache
		s::Send future
		d::Send trickedout
		f::Send sukhbir
		g::Send daisies
		h::Send dance
		j::Send pinata
	}
}
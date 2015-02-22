class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "/login"(view:"/login")
        "/home"(view:"/home")
        "500"(view:'/error')
	}
}

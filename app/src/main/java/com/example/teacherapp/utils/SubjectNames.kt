package com.example.teacherapp.utils

data class Subject(val name: String, val code: String)

val subjectsMap = mapOf(
    "COMPUTER" to mapOf(
        "1" to listOf(
            Subject("Applied Mechanics", "CE401"),
            Subject("Basic Electrical Engineering", "EE401"),
            Subject("Engineering Physics", "SH402"),
            Subject("Engineering Drawing I", "ME401"),
            Subject("Engineering Mathematics I", "SH401"),
            Subject("Computer Programming", "CT401")
        ),
        "2" to listOf(
            Subject("Engineering Mathematics II", "SH451"),
            Subject("Engineering Drawing II", "ME451"),
            Subject("Basic Electronics Engineering", "EX451"),
            Subject("Engineering Chemistry", "SH453"),
            Subject("Fundamental of Thermodynamics & Heat Transfer", "ME452"),
            Subject("Workshop Technology", "ME453")
        ),
        "3" to listOf(
            Subject("Engineering Mathematics III", "SH501"),
            Subject("Object Oriented Programming", "CT501"),
            Subject("Electric Circuit Theory", "EE501"),
            Subject("Theory of Computation", "CT502"),
            Subject("Electronics Devices and Circuit", "EX501"),
            Subject("Digital Logic", "EX502"),
            Subject("Electromagnetics", "EX503")
        ),
        "4" to listOf(
            Subject("Numerical Method", "SH553"),
            Subject("Applied Mathematics", "SH551"),
            Subject("Instrumentation I", "EE552"),
            Subject("Data Structure and Algorithm", "CT552"),
            Subject("Microprocessor", "EX551"),
            Subject("Discrete Structure", "CT551"),
            Subject("Electrical Machine", "EE554")
        ),
        "5" to listOf(
            Subject("Communication English", "SH601"),
            Subject("Probability and Statistics", "SH602"),
            Subject("Computer Organization and Architecture", "CT603"),
            Subject("Software Engineering", "CT601"),
            Subject("Computer Graphics", "EX603"),
            Subject("Instrumentation II", "EX602"),
            Subject("Data Communication", "CT602")
        ),
        "6" to listOf(
            Subject("Engineering Economics", "CE655"),
            Subject("Object Oriented Analysis and Design", "CT651"),
            Subject("Artificial Intelligence", "CT653"),
            Subject("Operating System", "CT656"),
            Subject("Embedded System", "CT655"),
            Subject("Database Management System", "CT652"),
            Subject("Minor Project", "CT654")
        ),
        "7" to listOf(
            Subject("ICT Project Management", "CT701"),
            Subject("Organization and Management", "ME708"),
            Subject("Energy Environment and Society", "EX701"),
            Subject("Distributed System", "CT703"),
            Subject("Computer Networks and Security", "CT702"),
            Subject("Digital Signal Analysis and Processing", "CT704"),
            Subject("Project (Part A)", "CT707"),
            Subject("Elective I: Advanced Java", "CT72501"),
            Subject("Elective I: Aeronautical Telecom", "EX72504"),
            Subject("Elective I: Biomedical Instrumentation", "C101"),
            Subject("Elective I: Data Mining", "april1"),
            Subject("Elective I: Embedded System", "april2"),
            Subject("Elective I: Image Processing", "CT72504"),
            Subject("Elective I: Microwave Engineering", "EX72505"),
            Subject("Elective I: Operating System", "april4"),
            Subject("Elective I: Radar Technology", "april5"),
            Subject("Elective I: Satellite Communication", "april6"),
            Subject("Elective I: Web Technology", "CT72505")
        ),
        "8" to listOf(
            Subject("Engineering Professional Practice", "CE752"),
            Subject("Information Systems", "CT751"),
            Subject("Internet and Intranet", "CT754"),
            Subject("Project (Part B)", "CT755"),
            Subject("Simulation and Modelling", "CT753"),
            Subject("Elective II: Advanced Computer Architecture", "CT76504"),
            Subject("Elective II: Agile Software Development", "CT76502"),
            Subject("Elective II: Big Data Technologies", "CT76507"),
            Subject("Elective II: Broadcast Engineering", "EX76503"),
            Subject("Elective II: Database Management Systems", "EX76506"),
            Subject("Elective II: Networking with IPv6", "CT76503"),
            Subject("Elective II: Optical Fiber Communication System", "EX76501"),
            Subject("Elective II: Wireless Communications", "EX76504"),
            Subject("Elective III: Artificial Intelligence", "CT78506"),
            Subject("Elective III: Enterprise Application Design and Development", "CT78504"),
            Subject("Elective III: Geographical Information System", "CT78507"),
            Subject("Elective III: Multimedia System", "CT78503"),
            Subject("Elective III: Power Electronics", "EE78507"),
            Subject("Elective III: Remote Sensing", "CT78501"),
            Subject("Elective III: Speech Processing", "CT78508"),
            Subject("Elective III: Telecommunication", "EX78503"),
            Subject("Elective III: XML: Foundations, Techniques and Applications", "CT78505")
        )
    ),
    "CIVIL" to mapOf(
        "1" to listOf(
            Subject("Engineering Drawing I", "ME401"),
            Subject("Engineering Mathematics I", "SH401"),
            Subject("Fundamental of Thermodynamics & Heat Transfer", "ME402"),
            Subject("Engineering Chemistry", "SH403"),
            Subject("Workshop Technology", "ME403"),
            Subject("Computer Programming", "CT401")
        ),
        "2" to listOf(
            Subject("Engineering Mathematics II", "SH451"),
            Subject("Basic Electronics Engineering", "EX451"),
            Subject("Engineering Drawing II", "ME451"),
            Subject("Engineering Physics", "SH452"),
            Subject("Applied Mechanics", "CE451"),
            Subject("Basic Electrical Engineering", "EE453")
        ),
        "3" to listOf(
            Subject("Civil Engineering Materials", "CE506"),
            Subject("Engineering Mathematics III", "SH501"),
            Subject("Applied Mechanics (Dynamics)", "CE503"),
            Subject("Engineering Geology I", "CE501"),
            Subject("Strength of Materials", "CE502"),
            Subject("Surveying I", "CE504"),
            Subject("Fluid Mechanics", "CE505")
        ),
        "4" to listOf(
            Subject("Hydraulics", "CE555"),
            Subject("Surveying II", "CE554"),
            Subject("Theory of Structures I", "CE551"),
            Subject("Probability And Statistics", "SH552"),
            Subject("Engineering Geology II", "CE553"),
            Subject("Building Drawing", "CE556"),
            Subject("Soil Mechanics", "CE552")
        ),
        "5" to listOf(
            Subject("Theory of Structures II", "CE601"),
            Subject("Water Supply Engineering", "CE605"),
            Subject("Engineering Hydrology", "CE606"),
            Subject("Concrete Technology and Masonry Structure", "CE603"),
            Subject("Numerical Methods", "SH603"),
            Subject("Foundation Engineering", "CE602"),
            Subject("Survey Camp", "CE604")
        ),
        "6" to listOf(
            Subject("Design of Steel and Timber Structure", "CE651"),
            Subject("Communication English", "SH651"),
            Subject("Engineering Economics", "CE655"),
            Subject("Building Technology", "CE652"),
            Subject("Sanitary Engineering", "CE656"),
            Subject("Transportation Engineering", "CE653"),
            Subject("Irrigation and Drainage", "CE654")
        ),
        "7" to listOf(
            Subject("Hydropower Engineering", "CE704"),
            Subject("Project Engineering", "CE701"),
            Subject("Transportation Engineering II", "CE703"),
            Subject("Estimating & Costing", "CE705"),
            Subject("Design of RCC Structure", "CE702"),
            Subject("Project (Part I)", "CE707"),
            Subject("Elective I: Bio-Engineering", "CE72504"),
            Subject("Elective I: Water and WasteWater Quality Analysis", "CE72512"),
            Subject("Elective I: Soil Conservation and Watershed Management", "CE72506"),
            Subject("Elective I: Trail Suspension Bridge", "CE72503"),
            Subject("Elective I: Structural Dynamics", "CE72501"),
            Subject("Elective I: Seismic Resistant Design of Masonry Structures", "CE72502"),
            Subject("Elective I: Solid Waste Management", "CE72511"),
            Subject("Elective I: Ropeway Engineering", "CE72510"),
            Subject("Elective I: Transportation Planning and Engineering", "CE72509"),
            Subject("Elective I: Rock Engineering", "CE72505"),
            Subject("Elective I: Community Development and Participatory Rural Approach", "CE72513"),
            Subject("Elective I: Earth Hazard", "CE72507")
        ),
        "8" to listOf(
            Subject("Technology Environment and Society", "CE753"),
            Subject("Construction Management", "CE754"),
            Subject("Project (Part II)", "CE755"),
            Subject("Engineering Professional Practice", "CE752"),
            Subject("Computational Techniques in Civil Engineering", "CE751"),
            Subject("Elective II: Climate Change", "CE76516"),
            Subject("Elective II: Environmental Management System", "CE76517"),
            Subject("Elective II: Groundwater Engineering", "CE76509"),
            Subject("Elective II: Seismic Risk Assessment", "CE76505"),
            Subject("Elective II: Water Quality Management", "CE76518"),
            Subject("Elective II: Traffic and Transport Modeling", "CE76510"),
            Subject("Elective II: Traffic Engineering and Management", "CE76513"),
            Subject("Elective II: Rural Road Engineering", "CE76514"),
            Subject("Elective II: Rock Slope Engineering", "CE76507"),
            Subject("Elective II: Rock Mechanics", "CE76511"),
            Subject("Elective II: Public Health and Risk Assessment", "CE76520"),
            Subject("Elective II: Vulnerability Assessment and Retrofitting Techniques", "CE76504"),
            Subject("Elective II: Design of Bridges", "CE76502"),
            Subject("Elective II: Introduction to Prestressed Concrete Analysis and Design", "CE76521"),
            Subject("Elective II: Post Disaster Water and Sanitation Management", "CE76519"),
            Subject("Elective II: Hill Irrigation Engineering", "CE76508"),
            Subject("Elective II: Geotechnical Earthquake Engineering", "CE76503"),
            Subject("Elective II: Advanced Geotechnical Engineering", "CE76512"),
            Subject("Elective II: Earthquake Resistant Design of Structures", "CE76501"),
            Subject("Elective II: Domestic Water and Waste Water Engineering and Management", "CE76515"),
            Subject("Elective III: Construction Safety Management", "CE78502"),
            Subject("Elective III: Procurement Management", "CE78503"),
            Subject("Elective III: Time Series Analysis", "CE78505"),
            Subject("Elective III: Environmental Impact Assessment (EIA)", "CE78504"),
            Subject("Elective III: GIS Application and Remote Sensing", "CE78501"),
            Subject("Elective III: Disaster Risk Management", "CE78506")
        )
    )
)

data class Section (val sec: String)
val sectionsMap = mapOf(
    "COMPUTER" to listOf(
        Section("AB"),
        Section("CD")
    ),
    "CIVIL" to listOf(
        Section("AB"),
        Section("CD"),
        Section("EF")

    )
)


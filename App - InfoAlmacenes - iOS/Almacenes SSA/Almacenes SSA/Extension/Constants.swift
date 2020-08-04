//
//  Constants.swift
//  Almacenes SSA
//
//  Created by Samuel Arturo Garrido Sánchez on 29/07/20.
//  Copyright © 2020 SamArtGS. All rights reserved.
//

import UIKit

enum Config {
    static let baseURL = NSURL(string: "http://www.example.org/")!
    static let splineReticulatorName = "foobar"
}

enum Color {
    static let primaryColor = UIColor(red: 0.22, green: 0.58, blue: 0.29, alpha: 1.0)
    static let secondaryColor = UIColor.lightGray

    // A visual way to define colours within code files is to use #colorLiteral
    // This syntax will present you with colour picker component right on the code line
    static let tertiaryColor = #colorLiteral(red: 0.22, green: 0.58, blue: 0.29, alpha: 1)
}

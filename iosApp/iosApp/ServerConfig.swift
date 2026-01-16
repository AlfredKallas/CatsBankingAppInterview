//
//  ServerConfig.swift
//  iosApp
//
//  Created by Alfred Kallas on 16/01/2026.
//


enum BaseUrl: String {
    case development = "https://cdf-test-mobile-default-rtdb..europe-west1.firebasedatabase.app"
}

class ServerConfig {
    static var shared: ServerConfig = ServerConfig()

    private init(){}
    
    private var baseUrl: String {
        #if development
        return BaseUrl.development.rawValue
        #else
        return BaseUrl.development.rawValue
        #endif
    }
    
    func getBaseUrl() -> String {
        return baseUrl
    }
    
    private var isDebug: Bool {
        #if DEBUG
            return true
        #else
            return false
        #endif
    }
    
    func isDebugMode() -> Bool {
        return isDebug
    }
}

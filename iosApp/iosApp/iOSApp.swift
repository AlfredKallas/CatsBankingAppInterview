import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        
        let serverConfig: ServerConfig = .shared
        
        KoinHelperKt.doInitKoin(
            isDebug: serverConfig.isDebugMode(),
            baseUrl: serverConfig.getBaseUrl(),
        )
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

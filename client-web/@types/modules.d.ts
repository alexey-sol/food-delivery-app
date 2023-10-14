import { type theme } from "@/app/style/theme";

type AppTheme = typeof theme;

declare module "styled-components" {
    interface DefaultTheme extends AppTheme {}
}

declare global {
    namespace NodeJS {
        interface ProcessEnv {
            NODE_ENV?: "development" | "production" | "test";
        }
    }
}

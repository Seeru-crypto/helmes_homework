import type {AppProps} from 'next/app'
import {ThemeProvider} from "styled-components";
import Layout from "../components/layout/Layout";
import {DayTheme} from "../styles/theme";

export default function App({Component, pageProps: {session, ...pageProps}}: AppProps) {
    return (
        <ThemeProvider theme={DayTheme}>
            <Layout>
                <Component {...pageProps}/>
            </Layout>
        </ThemeProvider>
    )
}

import type {AppProps} from 'next/app'
import {ThemeProvider} from "styled-components";
import Layout from "../components/layout/Layout";
import {message} from "antd";
import {DayTheme} from "../styles/theme";

export default function App({Component, pageProps: {session, ...pageProps}}: AppProps) {
    const [messageApi, contextHolder] = message.useMessage();

    return (
            <ThemeProvider theme={DayTheme}>
                <Layout>
                    {contextHolder}
                    <Component {...pageProps}/>
                </Layout>
            </ThemeProvider>
    )
}

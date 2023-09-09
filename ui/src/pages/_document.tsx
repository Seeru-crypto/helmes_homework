import {Head, Html, Main, NextScript} from 'next/document'

export default function Document() {
  return (
      <Html lang="en">
          <Head>
              <meta name="description" content="A template next.js app"/>
              <link rel="preconnect" href="https://fonts.googleapis.com"/>
              <link rel="preconnect" href="https://fonts.gstatic.com"/>
              <link
                  href="https://fonts.googleapis.com/css2?family=Inter:wght@500&family=Montserrat:wght@100;600;700&family=Poppins:wght@200&display=swap"
                  rel="stylesheet"/>
              <link rel="icon" href="/favicon.ico"/>
          </Head>
          <body>
          <Main/>
          <NextScript/>
          </body>
      </Html>
  )
}

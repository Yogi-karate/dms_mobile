import App, { Container } from 'next/app'
import React from 'react'
import withReduxStore from '../lib/with-redux-store'
import { Provider } from 'react-redux'
import { ThemeProvider } from '@material-ui/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import theme from '../lib/theme';

class MyApp extends App {
  render () {
    const { Component, pageProps, reduxStore } = this.props
    return (
      <Container>
        <Provider store={reduxStore}>
        <ThemeProvider theme={theme}>
        <CssBaseline />
          <Component {...pageProps} />
          </ThemeProvider>
        </Provider>
      </Container>
    )
  }
}

export default withReduxStore(MyApp)

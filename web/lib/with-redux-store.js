import React from 'react'
import { initializeStore } from './store'

const isServer = typeof window === 'undefined'
const __NEXT_REDUX_STORE__ = '__NEXT_REDUX_STORE__'

function getOrCreateStore(initialState) {
  // Always make a new store if server, otherwise state is shared between requests
  if (isServer) {
    return initializeStore(initialState)
  }

  // Create store if unavailable on the client and set it on the window object
  if (!window[__NEXT_REDUX_STORE__]) {
    window[__NEXT_REDUX_STORE__] = initializeStore(initialState)
  }
  return window[__NEXT_REDUX_STORE__]
}

export default App => {
  return class AppWithRedux extends React.Component {
    static async getInitialProps(appContext) {
      // Get or Create the store with `undefined` as initialState
      // This allows you to set a custom default initialState
     // console.log(" Init props in redux app");
      const reduxStore = getOrCreateStore()
     // console.log(" app context");
     // console.log(" app context",appContext);
      // Provide the store to getInitialProps of pages
      appContext.ctx.reduxStore = reduxStore
      let appProps = {}
      if (typeof App.getInitialProps === 'function') {
        appProps = await App.getInitialProps(appContext)
      }
     // console.log("State Initial ___>",reduxStore.getState());

      return {
        ...appProps,
        initialReduxState: reduxStore.getState()
      }
    }

    constructor(props) {
      super(props)
      console.log("redux constructor" , props);
      this.reduxStore = getOrCreateStore(props.initialReduxState)
    }

    render() {
    //  console.log("Rendering");
      return <App {...this.props} reduxStore={this.reduxStore} />
    }
  }
}

/* eslint react/prefer-stateless-function: 0 */

import React from 'react';
import PropTypes from 'prop-types';
import Head from 'next/head';
import { connect } from 'react-redux'
//import { startClock, serverRenderClock } from '../lib/store'
import withAuth from '../lib/withAuth';
import withLayout from '../lib/withLayout';
import dynamic from 'next/dynamic'
const Dashboard = dynamic(import('../components/dashboard'), { ssr: false })
//import Dashboard from '../components/dashboard';

class Index extends React.Component {
  static propTypes = {
    user: PropTypes.shape({
      email: PropTypes.string.isRequired,
    }),
  };

  static defaultProps = {
    user: null,
  };
  static getInitialProps ({ reduxStore, req }) {
    const isServer = !!req
    // DISPATCH ACTIONS HERE ONLY WITH `reduxStore.dispatch`
    reduxStore.dispatch(serverRenderClock(isServer))

    return {}
  }

  componentDidMount () {
    // DISPATCH ACTIONS HERE FROM `mapDispatchToProps`
    // TO TICK THE CLOCK
    this.timer = setInterval(() => this.props.startClock(), 1000)
  }

  componentWillUnmount () {
    clearInterval(this.timer)
  }
  render() {
    const { user } = this.props;
    console.log("User in index",user);
    return (
      <div style={{ padding: '10px 45px' }}>
        <Head>
          <title>Dashboard</title>
          <meta name="description" content="description for indexing bots" />
        </Head>
        <p> Dashboard </p>
        <Dashboard {...this.props} />
      </div>
    );
  }
}
const mapDispatchToProps = { startClock }
const mapStateToProps = state =>  {
  //console.log("The state in index",state)
  return {user:state.user};
}
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withAuth(withLayout(Index)));

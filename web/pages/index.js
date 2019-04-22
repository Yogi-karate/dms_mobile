/* eslint react/prefer-stateless-function: 0 */

import React from 'react';
import PropTypes from 'prop-types';
import Head from 'next/head';

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

  render() {
    const { user } = this.props;
    return (
      <div style={{ padding: '10px 45px' }}>
        <Head>
          <title>Dashboard</title>
          <meta name="description" content="description for indexing bots" />
        </Head>
        <p> Dashboard </p>
        <Dashboard {...this.props} />
        <p>Email: {user.email}</p>
      </div>
    );
  }
}

export default withAuth(withLayout(Index));

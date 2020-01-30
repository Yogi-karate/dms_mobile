/* eslint react/prefer-stateless-function: 0 */

import React from 'react';
import PropTypes from 'prop-types';
import Head from 'next/head';

import withAuth from '../../lib/withAuth';
import withLayout from '../../lib/withLayout';
import Leads from '../../components/dashboard/DailyLeads';
import DMSDashboard from '../../components/dashboard/DMSManagerDashboard';


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
          <title>Manager Dashboard</title>
          <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css"/>
          <meta name="description" content="description for indexing bots" />
        </Head>
        <p> Dashboard </p>
        <DMSDashboard {...this.props} />
      </div>
    );
  }
}

export default withAuth(withLayout(Index));

/* eslint react/prefer-stateless-function: 0 */

import React from 'react';
import PropTypes from 'prop-types';
import Head from 'next/head';

import withAuth from '../lib/withAuth';
import withLayout from '../lib/withLayout';

import SaleDataMainComponent from '../components/saleData/SaleDataMainComponent';


class SaleDataImport extends React.Component {
    //propTypes used to check the prop types(string,number etc) 
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
            <div style={{ padding: '10px 10px' }}>
                <Head>
                    <title>SaleData Form</title>
                    <meta name="saleData" content="displaying saleData" />
                </Head>
                <SaleDataMainComponent {...this.props} />
            </div>
        );
    }
}

export default withAuth(withLayout(SaleDataImport));

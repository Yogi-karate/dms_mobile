/* eslint react/prefer-stateless-function: 0 */

import React from 'react';
import PropTypes from 'prop-types';
import Head from 'next/head';

import withAuth from '../lib/withAuth';
import withLayout from '../lib/withLayout';

import PriceListMainComponent from '../components/priceList/PriceListMainComponent';


class PriceListImport extends React.Component {
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
                    <title>PriceList Form</title>
                    <meta name="priceList" content="displaying pricelist items" />
                </Head>
                <PriceListMainComponent {...this.props} />
            </div>
        );
    }
}

export default withAuth(withLayout(PriceListImport));

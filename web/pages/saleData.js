import React from "react";

import GridItem from "../components/common/Grid/GridItem.js";
import Grid from "@material-ui/core/Grid";
import SaleDataFormComponent from '../components/dashboard/SaleDataFormComponent';
import { connect } from 'react-redux';
import withLayout from '../lib/withLayout';
import withAuth from '../lib/withAuth';

class SaleData extends React.Component {
    constructor(props) {
        super(props);
        console.log("The props are in dasboar", props);
        this.props = props;
    }

    render() {
        return (
            <Grid direction="column">
                <GridItem xs={12} sm={12} md={12}>
                    <SaleDataFormComponent {... this.props} />
                </GridItem>
            </Grid>
        );
    }
}


export default withAuth(withLayout(SaleData));

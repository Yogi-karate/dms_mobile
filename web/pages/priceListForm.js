import React from "react";

import GridItem from "../components/common/Grid/GridItem.js";
import GridContainer from "../components/common/Grid/GridContainer.js";
import Grid from "@material-ui/core/Grid";
import PriceListFormComponent from '../components/dashboard/PriceListFormComponent';
import PriceListItemTable from '../components/dashboard/PriceListItemTable';
import { connect } from 'react-redux';
import withLayout from '../lib/withLayout';
import withAuth from '../lib/withAuth';

class PriceListForm extends React.Component {
    constructor(props) {
        super(props);
        console.log("The props are in dasboar", props);
        this.props = props;
    }

    render() {
        return (
            <Grid direction="column">
                <GridItem xs={12} sm={12} md={12}>
                    <PriceListFormComponent {... this.props} />
                </GridItem>
                <GridItem xs={12} sm={12} md={12}>
                    <PriceListItemTable {... this.props} />
                </GridItem>
            </Grid>
        );
    }
}


export default withAuth(withLayout(PriceListForm));

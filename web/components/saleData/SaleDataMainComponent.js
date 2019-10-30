import React from "react";

import GridItem from "../common/Grid/GridItem.js";
import Grid from "@material-ui/core/Grid";
import SaleDataFormComponent from './SaleDataFormComponent';
import { connect } from 'react-redux';

class SaleDataMainComponent extends React.Component {
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


export default SaleDataMainComponent;

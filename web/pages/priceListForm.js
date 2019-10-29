import React from "react";

import { Zoom } from "@material-ui/core";
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
        console.log("The showPriceListItemssssss in priceList form is ", this.props.showPriceListItems);
        return (
            <Grid direction="column">
                <GridItem xs={12} sm={12} md={12}>
                    <PriceListFormComponent {... this.props} />
                </GridItem>
                {(this.props.showPriceListItems == null || this.props.showPriceListItems == false) ?
                    (<GridItem xs={12} sm={12} md={12}>

                    </GridItem>) : (
                        <Zoom in={true} style={{ transitionDelay: true ? '500ms' : '0ms' }}>
                            <GridItem xs={12} sm={12} md={12}>
                                <PriceListItemTable {... this.props} />
                            </GridItem>
                        </Zoom>)}
            </Grid>
        );
    }
}

const mapStateToProps = (state) => {
    console.log("state in mapping in priceListForm main page", state);
    return { showPriceListItems: state.showPriceListItems };
}

export default connect(
    mapStateToProps,
    null
)(withAuth(withLayout(PriceListForm)));

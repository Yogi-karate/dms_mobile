import React from "react";

import { Zoom } from "@material-ui/core";
import GridItem from "../common/Grid/GridItem.js";
import Grid from "@material-ui/core/Grid";
import PriceListFormComponent from './PriceListFormComponent';
import PriceListItemTable from './PriceListItemTable';
import { connect } from 'react-redux';

class PriceListMainComponent extends React.Component {
    constructor(props) {
        super(props);
        console.log("The props are in dasboard", props);
        this.props = props;
    }

    render() {
        console.log("The showPriceListItemssssss in PriceListMainComponent is ", this.props.showPriceListItems);
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
    console.log("state in mapping in priceListImport main page", state);
    return { showPriceListItems: state.showPriceListItems };
}

export default connect(
    mapStateToProps,
    null
)(PriceListMainComponent);

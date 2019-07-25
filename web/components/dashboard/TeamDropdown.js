import React, { Component } from 'react';

import MUIDataTable from "mui-datatables";
import { createMuiTheme, MuiThemeProvider } from '@material-ui/core/styles';
import { getTeams } from '../../lib/api/admin';
import TRCustomDropDown from '../common/Dropdown';
import { team } from '../../lib/store';
import { connect } from 'react-redux';

const options = {
    filter: true,
    search: true,
    print: false,
    download: false,
    selectableRows: false,
    filterType: 'dropdown',
    responsive: 'stacked',
    rowsPerPage: 10,

};
class TeamDropDown extends Component {
    constructor(props) {
        super(props);
        this.props = props;
        console.log("dropdown props are", this.props);
        this.state = {
            teams: [],
        };
        this.onClick = this.onClick.bind(this);
        // this.handlePinChange = this.handlePinChange.bind(this);
    }
    onClick(param) {
        console.log(" On click method in parent", param);
        // let teams = this.state.teams
        this.props.team(param[1]);
    };
    async componentDidMount() {
        console.log("Inside getting teams");
        try {
            const data = await getTeams();
            console.log("The result is ", data);
            if (data.error === undefined || data.error === null || data.error === "") {
                // this.props.login(data);
                // document.location.pathname = "/"
            } else {
                console.log("Wrong input", this.props.errorValue);
                this.setState({ error: "Email/Password incorrect" });
            }
            let result = data.records.map(team => {
                return [team.name, team.id];
            })
            this.setState({ teams: result });
        } catch (err) {
            console.log(err); // eslint-disable-line
        }

    }

    render() {
        return (

            <TRCustomDropDown
                dropdown
                dropdownHeader="ALL TEAMS"
                buttonText="ALL TEAMS"
                buttonProps={{
                    round: true,
                    color: "success"
                }}
                dropdownList={this.state.teams}
                onClick={this.onClick}
            />
        );
    }

}
const mapStateToProps = state => {
    //const {app_state} = state;     
    console.log("state in mapping", state);
    return { team: state.team };
}
const mapDispatchToProps = { team }
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(TeamDropDown);
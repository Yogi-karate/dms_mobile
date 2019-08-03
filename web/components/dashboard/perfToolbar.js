import React, { Component } from 'react';
import Toolbar from '@material-ui/core/Toolbar';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import { withStyles } from '@material-ui/styles';
import MenuItem from "@material-ui/core/MenuItem";


import { getTeams } from '../../lib/api/admin';

import { changePerformanceFilters } from '../../lib/store';
import { connect } from 'react-redux';
import { getDashboardYear } from '../../lib/api/config';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faChartBar } from '@fortawesome/free-solid-svg-icons'

import { styleToolbar } from '../../lib/SharedStyles';

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

const styles = theme => ({
    textField: {
        width: '50%',
        position: 'relative',
        textAlign: 'left',
    },
    toolbar: {
        background: '#FFF',
        height: '64px',
        paddingRight: '20px',
        paddingTop: '20px',
        marginTop: theme.spacing(4),
    },
});

class PerformanceToolbar extends Component {
    constructor(props) {
        super(props);
        this.props = props;
        console.log("dropdown props are", this.props);
        let year_map = [];
        let year = new Date().getFullYear();
        let dashboard_year = getDashboardYear();
        while (dashboard_year <= year) {
            year_map.push(dashboard_year++);
        }
        let months_string = ['January',
            'February',
            'March',
            'April',
            'May',
            'June',
            'July',
            'August',
            'September',
            'October',
            'November',
            'December'];
        let months = months_string.map((month, key) => {
            return [month, key];
        })
        this.state = {
            filters: {
                teams: [],
                months: months,
                years: year_map,
            },
            teams: [],
            open: false,
            team: 12,
            month: "",
            year: "",
        };
        this.handleChange = this.handleChange.bind(this);
    }
    handleChange = prop => event => {
        if (this.state[prop] != event.target.value) {
            this.setState({ [prop]: event.target.value });
            console.log("The selected val is ", prop, event.target.value);
            console.log("The current state is -------->>>>>", this.state);
            let filter = { team: this.state.team, month: this.state.month, year: this.state.year };
            filter[prop] = event.target.value;
            this.props.changePerformanceFilters(filter);
        } else {
            console.log("Same value selected in performance filter -----");
        }
    };
    async componentDidMount() {
        console.log("Inside getting teams");
        try {
            console.log("Printing the user ", this.props.user);
            const data = await getTeams();
            console.log("The result is ", data);
            console.log("The result teams is ", data.result.teams.records);
            if (data == null || data == [] || data.result.teams.records == []) {
                this.setState({ teams: [], team: "" });
            } else {
                let result = data.result.teams.records.map(team => {
                    return [team.name, team.id];
                })
                console.log("The result teams is ", result[0][1]);
                let today = new Date();
                this.setState({ teams: result, team: result[0], month: today.getMonth(), year: today.getFullYear() });
                this.props.changePerformanceFilters({ team: this.state.team, month: this.state.month, year: this.state.year });
            }
        } catch (err) {
            console.log(err); // eslint-disable-line
        }
    }
    render() {
        const { classes } = this.props;
        return (
            <div>
                <Toolbar className={classes.toolbar}>
                    <Grid container direction="row">
                        <Grid item sm={5} xs={8} style={{ textAlign: 'left', fontSize: 30, color: '#388e3c' }}>
                            <FontAwesomeIcon icon={faChartBar} />
                            <b>&nbsp;&nbsp;Team Performance</b>
                        </Grid>
                        <Grid item sm={2} xs={1} style={{ textAlign: 'right' }}>
                            <TextField
                                select
                                className={classes.textField}
                                label="Month"
                                value={this.state.month}
                                onChange={this.handleChange('month')}
                            >
                                {this.state.filters.months.map(option => (
                                    <MenuItem key={option[1]} value={option[1]}>
                                        {option[0]}
                                    </MenuItem>
                                ))}
                            </TextField>
                        </Grid>
                        <Grid item sm={2} xs={1} style={{ textAlign: 'right' }}>
                            <TextField
                                select
                                className={classes.textField}
                                label="Year"
                                value={this.state.year}
                                onChange={this.handleChange('year')}
                            >
                                {this.state.filters.years.map((option, key) => (
                                    <MenuItem key={key} value={option}>
                                        {option}
                                    </MenuItem>
                                ))}
                            </TextField>
                        </Grid>
                        <Grid item sm={3} xs={2} style={{ textAlign: 'right' }}>
                            <TextField
                                select
                                className={classes.textField}
                                label="Team"
                                value={this.state.team}
                                onChange={this.handleChange('team')}
                            >
                                {this.state.teams.map(option => (
                                    <MenuItem key={option[1]} value={option}>
                                        {option[0]}
                                    </MenuItem>
                                ))}
                            </TextField>
                        </Grid>
                    </Grid>
                </Toolbar>
            </div>
        );
    }
}

const mapStateToProps = state => {
    //const {app_state} = state;     
    console.log("state in mapping", state);
    return { user: state.user };
}
const mapDispatchToProps = { changePerformanceFilters }
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(withStyles(styles)(PerformanceToolbar));
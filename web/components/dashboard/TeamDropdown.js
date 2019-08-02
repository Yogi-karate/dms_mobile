import React, { Component } from 'react';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from "@material-ui/core/MenuItem";
import MenuList from "@material-ui/core/MenuList";
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import InputAdornment from '@material-ui/core/InputAdornment';
import Grid from '@material-ui/core/Grid';
import { withStyles } from '@material-ui/styles';

import { getTeams } from '../../lib/api/admin';
import TRCustomDropDown from '../common/Dropdown';
import { team } from '../../lib/store';
import { connect } from 'react-redux';
import { boxShadow } from '../../lib/styles/material-kit-react';

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
        top: '-6px',
        textAlign: 'left',
    },
});

class TeamDropDown extends Component {
    constructor(props) {
        super(props);
        this.props = props;
        console.log("dropdown props are", this.props);
        this.state = {
            teams: [],
            classes: {
                textField: {
                    marginBottom: 4,
                    minwidth: true,
                },
            },
            open: false,
            team: 12,
        };
        this.handleTChange = this.handleTChange.bind(this);
    }
    handleTChange = prop => event => {
        this.setState({ [prop]: event.target.value });
        console.log("The selected val is ", event.target.value);
        this.props.team(event.target.value);
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
        const { classes } = this.props;
        return (
            <Grid container direction="row" justify="space-around" alignItems="center">
                <Grid item sm={6} xs={6} style={{ textAlign: 'right' }}>
                </Grid>
                <Grid item sm={6} xs={6} style={{ textAlign: 'right' }}>
                    <TextField
                        select
                        className={classes.textField}
                        label="Select a Team"
                        value={this.state.team}
                        onChange={this.handleTChange('team')}
                    >
                        {this.state.teams.map(option => (
                            <MenuItem key={option[1]} value={option[1]}>
                                {option[0]}
                            </MenuItem>
                        ))}
                    </TextField>
                </Grid>
            </Grid>
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
)(withStyles(styles)(TeamDropDown));
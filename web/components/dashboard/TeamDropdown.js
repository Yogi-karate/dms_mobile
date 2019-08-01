import React, { Component } from 'react';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from "@material-ui/core/MenuItem";
import MenuList from "@material-ui/core/MenuList";
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';

import { getTeams } from '../../lib/api/admin';
import TRCustomDropDown from '../common/Dropdown';
import { team,showDailyLeads } from '../../lib/store';
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
            classes: {
                button: {
                    display: 'block',
                    marginTop: 2,
                    fulwidth:true,
                },
                formControl: {
                    margin: 1,
                    fullWidth: true,
                    display:'flex',
                },
            },
            open: false,
            team: 12,
        };
        this.onClick = this.onClick.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.handleOpen = this.handleOpen.bind(this);

        // this.handlePinChange = this.handlePinChange.bind(this);
    }
    onClick(param) {
        console.log(" On click method in parent", param);
        // let teams = this.state.teams
        this.props.team(param[1]);
        this.props.showDailyLeads(false);
    };
    handleChange(event) {
        console.log("clicked team",event.target);
        //this.props.team(event.target.value);
        this.setState({age:event.target.value});
    }
    handleTeamChange(prop,key) {
        console.log("clicked team",prop,key);
        //this.props.team(event.target.value);
        this.setState({team:prop[0],open:false});
    }
    handleClose(evt) {
        //this.props.team(event.target.value);
        this.setState({open:false});
    }

    handleOpen() {
        this.setState({open:true});
    }
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
        const { classes } = this.state;
        return (
            <Grid container direction="row" justify="space-around" alignItems="center">
            <Grid item sm={9} xs={9} style={{ textAlign: 'right' }}>
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
                </Grid>
                <Grid item sm={1} xs={6} style={{ textAlign: 'right' }}>
                <form autoComplete="off">
                    <Button className={classes.button} onClick={this.handleOpen}>
                    </Button>
                    <FormControl className={classes.formControl}>
                        <InputLabel htmlFor="demo-controlled-open-select">Teams</InputLabel>
                        <Select
                            native
                            open={this.state.open}
                            onClose={this.handleClose}
                            onOpen={this.handleOpen}
                            value={this.state.team}
                            onChange={this.handleChange}
                            inputProps={{
                                name: 'teams',
                                id: 'demo-controlled-open-select',
                            }}
                        >
                    <MenuList>
                    {this.state.teams.map((prop, key) => {
                      return (
                        <MenuItem
                          key={prop[1]}
                        >
                          {prop[0]}
                        </MenuItem>
                      );
                    })}
                  </MenuList>
                        </Select>
                    </FormControl>
                </form>
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
const mapDispatchToProps = { team,showDailyLeads }
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(TeamDropDown);
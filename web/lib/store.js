import { createStore, applyMiddleware } from 'redux'
import { composeWithDevTools } from 'redux-devtools-extension'

const exampleInitialState = {
  lastUpdate: 0,
  light: false,
  count: 0,
  user: null,
  team: null,
  lead_state: null,
  dailyleads_userIndex: null,
  showDailyLeads: null,
  performanceFilters: {
    month: null,
    year: null,
    team: null,
  }
}

export const actionTypes = {
  LOGIN: 'LOGIN',
  TEAM_CHANGE: 'TEAM',
  PERF_FILTER: 'PERF_FILTER',
  LEAD_STATE: 'LEAD_STATE',
  DAILYLEADS_USERINDEX: 'DAILYLEADS_USERINDEX',
  SHOWDAILYLEADS: 'SHOWDAILYLEADS'
}

// REDUCERS
export const reducer = (state = exampleInitialState, action) => {
  switch (action.type) {
    case actionTypes.LOGIN:
      return Object.assign({}, state, {
        user: action.user
      })
    case actionTypes.TEAM_CHANGE:
      return Object.assign({}, state, {
        team: action.team
      })
    case actionTypes.LEAD_STATE:
      return Object.assign({}, state, {
        lead_state: action.leadstate
      })
    case actionTypes.DAILYLEADS_USERINDEX:
      return Object.assign({}, state, {
        dailyleads_userIndex: action.dailyLead
      })
    case actionTypes.SHOWDAILYLEADS:
      return Object.assign({}, state, {
        showDailyLeads: action.showDailyLeads
      })
    case actionTypes.PERF_FILTER:
      return Object.assign({}, state, {
        performanceFilters: action.performanceFilters
      })
    default:
      return state
  }
}

// ACTIONS
export const login = (user) => {
  console.log("Trying store login");
  return { type: actionTypes.LOGIN, user: user }
}
export const team = (team) => {
  console.log("Trying to change team");
  return { type: actionTypes.TEAM_CHANGE, team: team }
}
export const state_select = (leadstate) => {
  console.log("Trying to change enq stage");
  return { type: actionTypes.LEAD_STATE, leadstate: leadstate }
}
export const dailyleads_userIndex = (dailyLead) => {
  console.log("Trying to store daily lead row data");
  return { type: actionTypes.DAILYLEADS_USERINDEX, dailyLead: dailyLead }
}
export const showDailyLeads = (showDailyLeads) => {
  console.log("Trying to show daily leads");
  return { type: actionTypes.SHOWDAILYLEADS, showDailyLeads: showDailyLeads }
}
export const changePerformanceFilters = (filters) => {
  console.log("Trying to show daily leads");
  return { type: actionTypes.PERF_FILTER, performanceFilters: filters }
}


export function initializeStore(initialState = exampleInitialState) {
  return createStore(
    reducer,
    initialState,
    composeWithDevTools(applyMiddleware())
  )
}

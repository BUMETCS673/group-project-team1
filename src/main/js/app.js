import React from 'react';
import ReactDOM from 'react-dom';
import Container from 'react-bootstrap/Container'

class App extends React.Component {
	render() {
		return (
			<Container>
				Hello world! My wife is pregnant!!!
			</Container>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)
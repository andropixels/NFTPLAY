

// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";

contract GameToken is ERC20 {
    constructor() ERC20("PLAY", "GAME") {

    }

    function recived(address to, uint amount) public {

        _mint(to,amount);


    }
}


const apiUrl = 'http://localhost:8080/api/post';
const userApiUrl = 'http://localhost:8080/api/user';
let usuarioAtual = null;


document.addEventListener('DOMContentLoaded', () => {
    carregarUsuarioAtual();
    carregarPosts();
});

async function carregarUsuarioAtual() {
    try {
        const response = await fetch('http://localhost:8080/api/user/me', {
            method: 'GET',
            credentials: 'include',  
        });

        if (response.ok) {
            usuarioAtual = await response.json();
        } else {
            console.error('Erro ao carregar o usuário logado');
        }
    } catch (error) {
        console.error('Erro ao carregar usuário atual:', error);
    }
}

async function carregarPosts() {
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) throw new Error(`Erro na API: ${response.status}`);
        const posts = await response.json();

        const postsContainer = document.getElementById('postsContainer');
        postsContainer.innerHTML = '';

        posts.forEach(post => {
            const postDiv = document.createElement('div');
            postDiv.classList.add('post-list');
            postDiv.innerHTML = `
                <div class="user-info">
                    <img src="assets/IMG/user.png" alt="Avatar" class="avatar">
                    <div>
                        <span class="username">${post.usuario?.username || 'Anônimo'}</span>
                    </div>
                </div>
                <p class="post-content">${post.conteudo}</p>
                <div class="post-actions">
                    <span class="action like"> 👍 0</span>
                    <span class="action comment"> 💬 0</span>
                    <br>
                </div>
                <span class="post-time">${new Date(post.dataCriacao).toLocaleString('pt-BR', {
                weekday: 'short',
                year: 'numeric',
                month: '2-digit',
                day: '2-digit',
                hour: '2-digit',
                minute: '2-digit',
                hour12: false
            })}</span>
            `

            if (usuarioAtual && post.usuario.id === usuarioAtual.id) {
                const editButton = document.createElement('button');
                editButton.textContent = 'Editar';
                editButton.classList.add('btn_edit');
                editButton.onclick = () => editarPost(post.id);

                const deleteButton = document.createElement('button');
                deleteButton.textContent = 'Excluir';
                deleteButton.classList.add('btn_delete');
                deleteButton.onclick = () => excluirPost(post.id);

                postDiv.appendChild(editButton);
                postDiv.appendChild(deleteButton);
            }

            postsContainer.appendChild(postDiv);
        });
    } catch (error) {
        console.error('Erro ao carregar posts:', error);
    }
}

async function editarPost(postId) {
    const novoConteudo = prompt('Edite o conteúdo do post:');
    if (!novoConteudo) return;

    try {
        const response = await fetch(`${apiUrl}/${postId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ conteudo: novoConteudo })
        });

        const responseData = await response.json();

        if (!response.ok) {
            alert(`Erro ao editar o post: ${responseData.message || 'Erro desconhecido'}`);
        } else {
            carregarPosts();
        }
    } catch (error) {
        console.error('Erro ao editar post:', error);
    }
}

async function excluirPost(postId) {
    if (!confirm('Tem certeza de que deseja excluir este post?')) return;

    try {
        const response = await fetch(`${apiUrl}/${postId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            carregarPosts();
        } else {
            alert('Erro ao excluir o post.');
        }
    } catch (error) {
        console.error('Erro ao excluir post:', error);
    }
}

async function criarPost(event) {
    event.preventDefault();
    const conteudo = document.getElementById('conteudo').value;

    try {
        const response = await fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ conteudo })
        });

        if (response.ok) {
            document.getElementById('conteudo').value = '';
            carregarPosts();
        } else if(conteudo.length > 800){
            alert(`Limite de 800 caracteres ultrapassado, o conteudo atual possui: ${conteudo.length}`)
        } else {
            alert('Erro ao criar o post.');
        }
    } catch (error) {
        console.error('Erro ao criar post:', error);
    }
}

async function fazerLogout() {
    try {
        const response = await fetch('http://localhost:8080/api/user/logout', {
            method: 'POST',
            credentials: 'include',
        });

        if (response.ok) {
            alert('Você foi desconectado com sucesso!');
            window.location.href = 'index.html'; 
        } else {
            alert('Erro ao realizar logout. Tente novamente.');
        }
    } catch (error) {
        console.error('Erro ao realizar logout:', error);
        alert('Erro ao realizar logout. Tente novamente.');
    }
}

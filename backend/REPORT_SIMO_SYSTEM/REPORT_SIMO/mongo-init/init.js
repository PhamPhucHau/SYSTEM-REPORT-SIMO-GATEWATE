db = db.getSiblingDB('SIMO'); // tạo hoặc chọn database SIMO

// Tạo các collection rỗng nếu chưa có
const collections = [
  'API_1_6_tktt_dinh_ky',
  'LIST_FILE_UPLOAD',
  'ReportSystemSIMODSVNChannel', // tên đầy đủ bạn có thể sửa lại nếu cần
  'permissions',
  'role_permissions',
  'roles',
  'templates',
  'users'
];

collections.forEach(function (col) {
  if (!db.getCollectionNames().includes(col)) {
    db.createCollection(col);
  }
});

// Insert dữ liệu vào collection 'templates'
const templatesData = cat('/docker-entrypoint-initdb.d/init_templates.json');
db.templates.insertMany(JSON.parse(templatesData));

// Insert dữ liệu vào collection 'users'
const usersData = cat('/docker-entrypoint-initdb.d/init_users.json');
db.users.insertMany(JSON.parse(usersData));
